/** @type {import('tailwindcss').Config} */
export default {
    content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
    theme: {
        extend: {
            // para buscar las diferents propiedades es con alt espacio
            colors: {
                primary: "#0274AE",
                secondary: "#8FC9E3",
                tertiary: "#D5F4FE",
                grayPrimary: "#777777",
                graySecondary: "#555555",
                grayTertiary: "#343434",
                blackOpacity1: "rgba(0, 0, 0, 0.1)",
                blackOpacity5: "rgba(0, 0, 0, 0.5)",
                blackOpacity6: "rgba(0, 0, 0, 0.6)",
            },

            keyframes: {
                borderAnimation: {
                    "0%": { transform: "translateX(-100%)" },
                    "100%": { transform: "translateX(100%)" },
                },
            },
            animation: {
                borderAnimation: "borderAnimation 1s ease-in-out infinite",
            },
            // breakpoint personalizado para la tarjeta de productos
            screens: {
                ssm: "400px",
            },
        },
    },
    plugins: [],
};
