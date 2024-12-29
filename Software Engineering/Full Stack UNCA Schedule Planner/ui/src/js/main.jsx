import { React } from "react";
import { createRoot } from "react-dom/client";
import App from "./App.jsx";
import "../css/tailwind.css";

function main() {
    const root = createRoot(document.getElementById("app"));
    root.render(<App />);
}

// Invoke the function that kicks off React!
main();
