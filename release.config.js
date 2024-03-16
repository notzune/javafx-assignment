module.exports = {
  branches: [process.env.DEFAULT_BRANCH || 'main'], // Use an environment variable for the default branch
  repositoryUrl: process.env.REPO_URL, // Set the repository URL via environment variable

  plugins: [
    '@semantic-release/commit-analyzer', // for analyzing commit messages
    '@semantic-release/release-notes-generator', // for generating release notes
    [
      '@semantic-release/changelog', // for updating the CHANGELOG.md file
      {
        changelogFile: process.env.CHANGELOG_FILE || 'CHANGELOG.md',
      },
    ],
    [
      '@semantic-release/git', // for committing and pushing changes
      {
        assets: ['CHANGELOG.md', 'package.json'],
        message: `chore(release): \${nextRelease.version} [skip ci]\n\n\${nextRelease.notes}`
      }
    ],
    '@semantic-release/github' // for creating a GitHub release
  ]
};
