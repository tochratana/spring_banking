#!/bin/bash

# Ask for commit message
echo "Enter commit message:"
read commit_message

# Add all changes
git add .

# Commit with the message
git commit -m "$commit_message"

# Push to the current branch
git push origin main

echo "✅ Changes pushed successfully!"
