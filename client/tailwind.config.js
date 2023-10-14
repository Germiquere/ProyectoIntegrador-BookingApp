/** @type {import('tailwindcss').Config} */
export default {
    content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
    theme: {
        extend: {
            // para buscar las diferents propiedades es con alt espacio
            colors: {
                primary: "#ffffff",
                secondary: "#0D3460",
                tertiary: "#C5C5C5",
                purple: "#5A1F88",
            },
        },
    },
    plugins: [],
};
