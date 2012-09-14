#!/bin/sh

git rm -f common/*.iml
git rm -f core/*.iml
git rm -f test-common/*.iml
git rm -f webapp/*.iml
git rm -f ./*.iml
git rm -rf .idea

rm common/*.iml
rm core/*.iml
rm test-common/*.iml
rm webapp/*.iml
rm ./*.iml
rm -rf .idea

