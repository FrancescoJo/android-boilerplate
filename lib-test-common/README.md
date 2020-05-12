# lib-test-common

Testing library for all modules in this project. Normally, this module should be included in `app-lib`
and there is no need to maintain it. However, currently(Jan - 2020) Android build tools does not 
support test artifact build per module.

This module is to mitigate such a problem to reuse test utilities in test logic across every modules.
