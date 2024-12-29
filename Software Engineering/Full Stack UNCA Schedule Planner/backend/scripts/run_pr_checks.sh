# This script runs all of the same checks that 
# GitHub will run to determine whether your PR has
# This script runs all of the same checks that 
# GitHub will run to determine whether your PR has
# passed the minimum code quality standards.

script_name=$0
script_full_path=$(dirname "$0")

# run check script:
bash $script_full_path"/check.sh"

# run tests:
poetry run pytest -v 