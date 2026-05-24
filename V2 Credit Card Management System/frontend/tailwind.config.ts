import type { Config } from "tailwindcss";

export default {
  content: ["./index.html", "./src/**/*.{ts,tsx}"],
  darkMode: "class",
  theme: {
    extend: {
      colors: {
        ink: "#17202a",
        bank: "#005b96",
        mint: "#0f8b8d",
        risk: "#b42318",
        warning: "#b7791f",
        success: "#067647"
      },
      boxShadow: {
        panel: "0 1px 3px rgba(15, 23, 42, 0.08)"
      }
    }
  },
  plugins: []
} satisfies Config;

