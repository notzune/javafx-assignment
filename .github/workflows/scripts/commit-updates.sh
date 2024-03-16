#!/bin/bash

# Configure Git with GitHub Actions user
git config --local user.email "action@github.com"
git config --local user.name "GitHub Action"

# Fetch the latest changes from the origin
git fetch origin

# Check out the branch you're working on
# Replace 'main' with your branch name if it's different
git checkout main

# Merge changes from the remote branch
git merge origin/main

# Add and commit changes
git add pom.xml
git commit -m "ci: update pom.xml version to $RELEASE_VERSION" || echo "No changes to commit"

# Push changes back to the remote repository
git push origin main