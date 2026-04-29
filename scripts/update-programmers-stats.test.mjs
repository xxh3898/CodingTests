import assert from 'node:assert/strict';
import fs from 'node:fs/promises';
import os from 'node:os';
import path from 'node:path';
import test from 'node:test';

import { collectProgrammersStats, writeProgrammersOutputs } from './update-programmers-stats.mjs';

test('should_collectProgrammersStats_when_programmersDirectoriesExist', async () => {
  const rootDir = await createFixtureRepository();
  await writeProblem({
    rootDir,
    level: 'lv0',
    directoryName: '123. 문자열 출력',
    title: '문자열 출력',
    id: '123',
    category: '코딩테스트 연습 > 코딩 기초 트레이닝',
    submittedAt: '2026년 04월 30일 01:41:31',
    solutionFile: '문자열 출력.java',
  });
  await writeProblem({
    rootDir,
    level: 'lv2',
    directoryName: '456. 조건별 집계',
    title: '조건별 집계',
    id: '456',
    category: '코딩테스트 연습 > GROUP BY',
    submittedAt: '2026년 04월 30일 01:42:31',
    solutionFile: '조건별 집계.sql',
  });

  const stats = await collectProgrammersStats(rootDir, new Date('2026-04-30T00:00:00.000Z'));

  assert.equal(stats.totalSolved, 2);
  assert.equal(stats.customScore, 8);
  assert.deepEqual(stats.levels, { lv0: 1, lv2: 1 });
  assert.deepEqual(stats.languages, { Java: 1, SQL: 1 });
  assert.equal(stats.latest[0].id, '456');
  assert.equal(stats.latest[0].levelLabel, 'Lv. 2');
});

test('should_writeProgrammersOutputs_when_statsAreCollected', async () => {
  const rootDir = await createFixtureRepository();
  await writeProblem({
    rootDir,
    level: 'lv1',
    directoryName: '789. 숫자 짝꿍',
    title: '숫자 짝꿍',
    id: '789',
    category: '코딩테스트 연습 > 연습문제',
    submittedAt: '2026년 04월 30일 01:43:31',
    solutionFile: '숫자 짝꿍.java',
  });
  const stats = await collectProgrammersStats(rootDir, new Date('2026-04-30T00:00:00.000Z'));

  await writeProgrammersOutputs(rootDir, stats);

  const statsJson = JSON.parse(await fs.readFile(path.join(rootDir, 'data/programmers-stats.json')));
  const solvedBadge = JSON.parse(
    await fs.readFile(path.join(rootDir, 'badges/programmers-solved.json')),
  );
  const card = await fs.readFile(path.join(rootDir, 'assets/programmers-card.svg'), 'utf8');

  assert.equal(statsJson.totalSolved, 1);
  assert.equal(solvedBadge.schemaVersion, 1);
  assert.equal(solvedBadge.message, '1 solved');
  assert.match(card, /Programmers Progress/);
  assert.match(card, /CUSTOM SCORE/);
});

async function createFixtureRepository() {
  return fs.mkdtemp(path.join(os.tmpdir(), 'programmers-stats-'));
}

async function writeProblem({
  rootDir,
  level,
  directoryName,
  title,
  id,
  category,
  submittedAt,
  solutionFile,
}) {
  const problemDir = path.join(rootDir, '프로그래머스', level, directoryName);
  await fs.mkdir(problemDir, { recursive: true });
  await fs.writeFile(
    path.join(problemDir, 'README.md'),
    `# [${level}] ${title} - ${id}

[문제 링크](https://school.programmers.co.kr/learn/courses/30/lessons/${id})

### 구분

${category}

### 제출 일자

${submittedAt}
`,
    'utf8',
  );
  await fs.writeFile(path.join(problemDir, solutionFile), 'class Solution {}\n', 'utf8');
}
