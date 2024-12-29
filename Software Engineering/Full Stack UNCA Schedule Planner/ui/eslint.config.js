import globals from "globals";
import pluginJs from "@eslint/js";
import mochaPlugin from "eslint-plugin-mocha";

export default [
    {
        languageOptions: {
            globals: {
                // indicate that this is a Node.js project and
                // not a browser project:
                ...globals.node,

                // Mocha globals
                describe: "readonly",
                it: "readonly",
                before: "readonly",
                after: "readonly",
                beforeEach: "readonly",
                afterEach: "readonly",
            },
        },
        plugins: {
            mocha: mochaPlugin,
        },
        rules: {
            // Example rule from Mocha plugin (optional)
            "mocha/no-exclusive-tests": "error",
            indent: ["error", 4], // Set indentation to 4 spaces
            //   "no-console": "off", // Disable console errors or warnings
        },
    },
    pluginJs.configs.recommended,
];
