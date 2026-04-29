import fs from 'node:fs/promises';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const PROGRAMMERS_ROOTS = ['프로그래머스', 'Programmers'];

const SCORE_WEIGHTS = {
  lv0: 1,
  lv1: 3,
  lv2: 7,
  lv3: 15,
  lv4: 31,
  lv5: 63,
};

const LANGUAGE_BY_EXTENSION = {
  '.c': 'C',
  '.cc': 'C++',
  '.cpp': 'C++',
  '.cs': 'C#',
  '.go': 'Go',
  '.java': 'Java',
  '.js': 'JavaScript',
  '.kt': 'Kotlin',
  '.php': 'PHP',
  '.py': 'Python',
  '.rb': 'Ruby',
  '.sql': 'SQL',
  '.swift': 'Swift',
  '.ts': 'TypeScript',
};

const OUTPUT_PATHS = {
  stats: 'data/programmers-stats.json',
  card: 'assets/programmers-card.svg',
  solvedBadge: 'badges/programmers-solved.json',
  scoreBadge: 'badges/programmers-score.json',
  levelsBadge: 'badges/programmers-levels.json',
};

export async function main({ rootDir = process.cwd(), now = new Date() } = {}) {
  const stats = await collectProgrammersStats(rootDir, now);
  await writeProgrammersOutputs(rootDir, stats);
  return stats;
}

export async function collectProgrammersStats(rootDir, now = new Date()) {
  const problems = [];
  const seenProblems = new Set();
  const sourceRoots = [];

  for (const rootName of PROGRAMMERS_ROOTS) {
    const programmersRoot = path.join(rootDir, rootName);
    if (!(await isDirectory(programmersRoot))) {
      continue;
    }

    sourceRoots.push(rootName);
    const levelDirs = await readSortedDirectory(programmersRoot);

    for (const levelEntry of levelDirs) {
      if (!levelEntry.isDirectory()) {
        continue;
      }

      const level = normalizeLevel(levelEntry.name);
      if (!level) {
        continue;
      }

      const levelDir = path.join(programmersRoot, levelEntry.name);
      const problemDirs = await readSortedDirectory(levelDir);

      for (const problemEntry of problemDirs) {
        if (!problemEntry.isDirectory()) {
          continue;
        }

        const problemDir = path.join(levelDir, problemEntry.name);
        const problem = await parseProblemDirectory({
          rootDir,
          rootName,
          level,
          problemDir,
          problemDirName: problemEntry.name,
        });

        const problemKey = `${problem.level}:${problem.id ?? problem.relativePath}`;
        if (seenProblems.has(problemKey)) {
          continue;
        }

        seenProblems.add(problemKey);
        problems.push(problem);
      }
    }
  }

  const levelCounts = countBy(problems, (problem) => problem.level);
  const languageCounts = countLanguages(problems);
  const customScore = problems.reduce(
    (score, problem) => score + (SCORE_WEIGHTS[problem.level] ?? 0),
    0,
  );
  const latest = [...problems]
    .sort((left, right) => {
      const rightTime = right.submittedAtTimestamp ?? 0;
      const leftTime = left.submittedAtTimestamp ?? 0;
      if (rightTime !== leftTime) {
        return rightTime - leftTime;
      }

      return left.relativePath.localeCompare(right.relativePath, 'ko');
    })
    .slice(0, 5)
    .map(({ submittedAtTimestamp, ...problem }) => problem);

  return {
    generatedAt: now.toISOString(),
    generatedAtKst: formatKstDateTime(now),
    source: {
      repository: 'xxh3898/CodingTests',
      roots: sourceRoots,
      basePath: '프로그래머스/<level>/<problem>',
    },
    totalSolved: problems.length,
    customScore,
    scoreWeights: SCORE_WEIGHTS,
    levels: sortObject(levelCounts, compareLevels),
    languages: sortObject(languageCounts, compareCountThenName),
    latest,
  };
}

async function parseProblemDirectory({ rootDir, rootName, level, problemDir, problemDirName }) {
  const problemFiles = await readSortedDirectory(problemDir);
  const readmePath = path.join(problemDir, 'README.md');
  const readme = (await isFile(readmePath)) ? await fs.readFile(readmePath, 'utf8') : '';
  const folderMetadata = parseProblemFolderName(problemDirName);
  const readmeMetadata = parseReadmeMetadata(readme);
  const solutionFiles = problemFiles
    .filter((entry) => entry.isFile() && entry.name.toLowerCase() !== 'readme.md')
    .map((entry) => entry.name);
  const languages = [...new Set(solutionFiles.map(detectLanguage).filter(Boolean))].sort(
    compareNames,
  );
  const relativePath = path.posix.join(
    rootName,
    level,
    toPosixPath(path.relative(path.join(rootDir, rootName, level), problemDir)),
  );

  return {
    id: readmeMetadata.id ?? folderMetadata.id,
    title: readmeMetadata.title ?? folderMetadata.title ?? normalizeText(problemDirName),
    level,
    levelLabel: formatLevelLabel(level),
    category: readmeMetadata.category,
    submittedAt: readmeMetadata.submittedAt,
    submittedAtIso: readmeMetadata.submittedAtIso,
    submittedAtTimestamp: readmeMetadata.submittedAtTimestamp,
    url: readmeMetadata.url,
    languages,
    solutionFiles,
    relativePath,
  };
}

export async function writeProgrammersOutputs(rootDir, stats) {
  const outputs = {
    [OUTPUT_PATHS.stats]: `${JSON.stringify(stats, null, 2)}\n`,
    [OUTPUT_PATHS.card]: renderCardSvg(stats),
    [OUTPUT_PATHS.solvedBadge]: `${JSON.stringify(
      createBadge({
        label: 'Programmers',
        message: `${stats.totalSolved} solved`,
        color: '3b82f6',
      }),
      null,
      2,
    )}\n`,
    [OUTPUT_PATHS.scoreBadge]: `${JSON.stringify(
      createBadge({
        label: 'PG score',
        message: `${stats.customScore} pts`,
        color: '16a34a',
      }),
      null,
      2,
    )}\n`,
    [OUTPUT_PATHS.levelsBadge]: `${JSON.stringify(
      createBadge({
        label: 'PG levels',
        message: formatLevelsBadgeMessage(stats.levels),
        color: '7c3aed',
      }),
      null,
      2,
    )}\n`,
  };

  for (const [relativePath, content] of Object.entries(outputs)) {
    const outputPath = path.join(rootDir, relativePath);
    await fs.mkdir(path.dirname(outputPath), { recursive: true });
    await fs.writeFile(outputPath, content, 'utf8');
  }
}

function renderCardSvg(stats) {
  const width = 720;
  const height = 430;
  const levels = ensureKnownLevels(stats.levels);
  const maxLevelCount = Math.max(1, ...Object.values(levels));
  const languageEntries = Object.entries(stats.languages).slice(0, 4);
  const latestEntries = stats.latest.slice(0, 4);
  const levelGroupY = 236;
  const latestStartY = 350;

  const levelRows = Object.entries(levels)
    .map(([level, count], index) => {
      const x = 38 + index * 108;
      const barWidth = Math.round((count / maxLevelCount) * 74);

      return `
        <g transform="translate(${x} ${levelGroupY})">
          <text x="0" y="0" class="muted">${xmlEscape(formatLevelLabel(level))}</text>
          <rect x="0" y="14" width="74" height="8" rx="4" fill="#243044"/>
          <rect x="0" y="14" width="${barWidth}" height="8" rx="4" fill="${levelColor(index)}"/>
          <text x="0" y="50" class="level-count">${count}</text>
        </g>`;
    })
    .join('');

  const languageText =
    languageEntries.length > 0
      ? languageEntries.map(([language, count]) => `${language} ${count}`).join(' · ')
      : 'No languages yet';
  const latestRows =
    latestEntries.length > 0
      ? latestEntries
          .map((problem, index) => {
            const y = latestStartY + index * 20;
            const language = problem.languages[0] ? ` · ${problem.languages[0]}` : '';
            const text = `${formatLevelLabel(problem.level)} · ${truncate(problem.title, 28)}${language}`;

            return `<text x="38" y="${y}" class="latest">${xmlEscape(text)}</text>`;
          })
          .join('\n')
      : `<text x="38" y="${latestStartY}" class="latest">No solved problems yet</text>`;

  return `<svg width="${width}" height="${height}" viewBox="0 0 ${width} ${height}" fill="none" xmlns="http://www.w3.org/2000/svg" role="img" aria-labelledby="title desc">
  <title id="title">Programmers progress card</title>
  <desc id="desc">Repository-driven Programmers solving stats for xxh3898/CodingTests</desc>
  <style>
    .title { fill: #f8fafc; font: 700 28px 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', sans-serif; }
    .subtitle { fill: #94a3b8; font: 500 13px 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', sans-serif; }
    .metric-label { fill: #94a3b8; font: 600 12px 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', sans-serif; letter-spacing: 0; }
    .metric-value { fill: #f8fafc; font: 800 32px 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', sans-serif; }
    .muted { fill: #cbd5e1; font: 700 12px 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', sans-serif; }
    .level-count { fill: #f8fafc; font: 800 20px 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', sans-serif; }
    .latest { fill: #dbeafe; font: 600 13px 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', sans-serif; }
    .small { fill: #94a3b8; font: 600 12px 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', sans-serif; }
  </style>
  <rect width="${width}" height="${height}" rx="18" fill="#0f172a"/>
  <rect x="1" y="1" width="${width - 2}" height="${height - 2}" rx="17" stroke="#1e293b"/>

  <text x="38" y="52" class="title">Programmers Progress</text>
  <text x="38" y="76" class="subtitle">Generated from xxh3898/CodingTests repository files</text>
  <text x="38" y="99" class="small">Updated ${xmlEscape(stats.generatedAtKst)}</text>

  <g transform="translate(38 126)">
    <text x="0" y="0" class="metric-label">SOLVED</text>
    <text x="0" y="42" class="metric-value">${stats.totalSolved}</text>
  </g>
  <g transform="translate(220 126)">
    <text x="0" y="0" class="metric-label">CUSTOM SCORE</text>
    <text x="0" y="42" class="metric-value">${stats.customScore}</text>
  </g>
  <g transform="translate(438 126)">
    <text x="0" y="0" class="metric-label">LANGUAGES</text>
    <text x="0" y="29" class="latest">${xmlEscape(truncate(languageText, 32))}</text>
  </g>

  <text x="38" y="216" class="metric-label">LEVEL BREAKDOWN</text>
  ${levelRows}

  <text x="38" y="326" class="metric-label">LATEST SOLVED</text>
  ${latestRows}
</svg>
`;
}

function parseReadmeMetadata(readme) {
  if (!readme) {
    return {};
  }

  const heading = readme.match(/^#\s+\[[^\]]+\]\s+(.+?)\s+-\s+(\d+)\s*$/m);
  const link = readme.match(/\[문제 링크\]\(([^)]+)\)/);
  const submittedAt = readSectionValue(readme, '제출 일자');

  return {
    id: heading?.[2],
    title: heading?.[1] ? normalizeText(heading[1]) : undefined,
    url: link?.[1],
    category: readSectionValue(readme, '구분'),
    submittedAt,
    ...parseSubmittedAt(submittedAt),
  };
}

function parseProblemFolderName(name) {
  const match = name.match(/^(\d+)\.\s*(.+)$/u);
  if (!match) {
    return {};
  }

  return {
    id: match[1],
    title: normalizeText(match[2]),
  };
}

function readSectionValue(markdown, sectionName) {
  const lines = markdown.split(/\r?\n/u);
  const sectionIndex = lines.findIndex((line) => normalizeText(line) === `### ${sectionName}`);
  if (sectionIndex === -1) {
    return undefined;
  }

  for (let index = sectionIndex + 1; index < lines.length; index += 1) {
    const line = normalizeText(lines[index]);
    if (!line) {
      continue;
    }

    if (line.startsWith('### ')) {
      return undefined;
    }

    return line;
  }

  return undefined;
}

function parseSubmittedAt(submittedAt) {
  if (!submittedAt) {
    return {};
  }

  const match = submittedAt.match(
    /(\d{4})년\s*(\d{1,2})월\s*(\d{1,2})일\s*(\d{1,2}):(\d{1,2}):(\d{1,2})/u,
  );
  if (!match) {
    return {};
  }

  const [, year, month, day, hour, minute, second] = match.map(Number);
  const submittedAtIso = `${String(year).padStart(4, '0')}-${String(month).padStart(
    2,
    '0',
  )}-${String(day).padStart(2, '0')}T${String(hour).padStart(2, '0')}:${String(minute).padStart(
    2,
    '0',
  )}:${String(second).padStart(2, '0')}+09:00`;

  return {
    submittedAtIso,
    submittedAtTimestamp: Date.parse(submittedAtIso),
  };
}

function normalizeLevel(name) {
  const match = name.toLowerCase().match(/^lv\s*([0-9]+)$/u);
  if (!match) {
    return undefined;
  }

  return `lv${Number(match[1])}`;
}

function formatLevelLabel(level) {
  const match = level.match(/^lv([0-9]+)$/u);
  return match ? `Lv. ${match[1]}` : 'Unknown';
}

function ensureKnownLevels(levels) {
  const knownLevels = {};
  for (const level of Object.keys(SCORE_WEIGHTS)) {
    knownLevels[level] = levels[level] ?? 0;
  }

  for (const [level, count] of Object.entries(levels)) {
    if (!(level in knownLevels)) {
      knownLevels[level] = count;
    }
  }

  return knownLevels;
}

function countBy(items, keySelector) {
  return items.reduce((counts, item) => {
    const key = keySelector(item);
    counts[key] = (counts[key] ?? 0) + 1;
    return counts;
  }, {});
}

function countLanguages(problems) {
  return problems.reduce((counts, problem) => {
    for (const language of problem.languages) {
      counts[language] = (counts[language] ?? 0) + 1;
    }

    return counts;
  }, {});
}

function detectLanguage(fileName) {
  const extension = path.extname(fileName).toLowerCase();
  return LANGUAGE_BY_EXTENSION[extension] ?? extension.replace('.', '').toUpperCase();
}

function createBadge({ label, message, color }) {
  return {
    schemaVersion: 1,
    label,
    message,
    color,
    labelColor: '111827',
  };
}

function formatLevelsBadgeMessage(levels) {
  const entries = Object.entries(ensureKnownLevels(levels)).filter(([, count]) => count > 0);
  if (entries.length === 0) {
    return 'no solved problems';
  }

  return entries.map(([level, count]) => `${formatCompactLevelLabel(level)}: ${count}`).join(' · ');
}

function formatCompactLevelLabel(level) {
  const match = level.match(/^lv([0-9]+)$/u);
  return match ? `Lv${match[1]}` : 'Unknown';
}

async function isDirectory(filePath) {
  try {
    return (await fs.stat(filePath)).isDirectory();
  } catch {
    return false;
  }
}

async function isFile(filePath) {
  try {
    return (await fs.stat(filePath)).isFile();
  } catch {
    return false;
  }
}

async function readSortedDirectory(dirPath) {
  return (await fs.readdir(dirPath, { withFileTypes: true })).sort((left, right) =>
    left.name.localeCompare(right.name, 'ko'),
  );
}

function sortObject(object, compare) {
  return Object.fromEntries(Object.entries(object).sort(compare));
}

function compareLevels([left], [right]) {
  return levelNumber(left) - levelNumber(right);
}

function compareCountThenName([leftName, leftCount], [rightName, rightCount]) {
  if (rightCount !== leftCount) {
    return rightCount - leftCount;
  }

  return compareNames(leftName, rightName);
}

function compareNames(left, right) {
  return left.localeCompare(right, 'ko');
}

function levelNumber(level) {
  const match = level.match(/^lv([0-9]+)$/u);
  return match ? Number(match[1]) : Number.MAX_SAFE_INTEGER;
}

function levelColor(index) {
  return ['#60a5fa', '#34d399', '#fbbf24', '#fb7185', '#c084fc', '#38bdf8'][index % 6];
}

function normalizeText(value) {
  return String(value)
    .replace(/[\u2000-\u200b\u202f\u205f\u3000]+/gu, ' ')
    .replace(/\s+/gu, ' ')
    .trim();
}

function truncate(value, maxLength) {
  const chars = [...String(value)];
  if (chars.length <= maxLength) {
    return String(value);
  }

  return `${chars.slice(0, Math.max(0, maxLength - 1)).join('')}…`;
}

function xmlEscape(value) {
  return String(value)
    .replaceAll('&', '&amp;')
    .replaceAll('<', '&lt;')
    .replaceAll('>', '&gt;')
    .replaceAll('"', '&quot;');
}

function toPosixPath(value) {
  return value.split(path.sep).join(path.posix.sep);
}

function formatKstDateTime(date) {
  const kst = new Date(date.getTime() + 9 * 60 * 60 * 1000);
  const year = kst.getUTCFullYear();
  const month = String(kst.getUTCMonth() + 1).padStart(2, '0');
  const day = String(kst.getUTCDate()).padStart(2, '0');
  const hour = String(kst.getUTCHours()).padStart(2, '0');
  const minute = String(kst.getUTCMinutes()).padStart(2, '0');

  return `${year}.${month}.${day} ${hour}:${minute} KST`;
}

const executedFile = process.argv[1] ? path.resolve(process.argv[1]) : undefined;
const currentFile = fileURLToPath(import.meta.url);

if (executedFile === currentFile) {
  main().catch((error) => {
    console.error(error);
    process.exitCode = 1;
  });
}
