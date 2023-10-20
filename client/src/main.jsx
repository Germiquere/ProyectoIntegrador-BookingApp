import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import "./index.css";
import { BrowserRouter } from "react-router-dom";
import { CalendarAndSearchProvider } from "./context/CalendarSearchContext.jsx";
import { CategoriesProvider } from "./context/CategoriesContext.jsx";
import { BikesProvider } from "./context/BikesContext.jsx";

ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <BrowserRouter>
            <CalendarAndSearchProvider>
                <CategoriesProvider>
                    <BikesProvider>
                        <App />
                    </BikesProvider>
                </CategoriesProvider>
            </CalendarAndSearchProvider>
        </BrowserRouter>
    </React.StrictMode>
);
