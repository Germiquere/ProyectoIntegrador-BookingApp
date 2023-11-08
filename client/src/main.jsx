import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import "./index.css";
import { BrowserRouter } from "react-router-dom";
import { CalendarAndSearchProvider } from "./context/CalendarSearchContext.jsx";
import { CategoriesProvider } from "./context/CategoriesContext.jsx";
import { BikesProvider } from "./context/BikesContext.jsx";
import { UsersProvider } from "./context/UsersContext.jsx";
import { CharacteristicsProvider } from "./context/CharacteristicsContext.jsx";

ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <BrowserRouter>
            <CalendarAndSearchProvider>
                <CategoriesProvider>
                    <BikesProvider>
                        <UsersProvider>
                            <CharacteristicsProvider>
                                <App />
                            </CharacteristicsProvider>
                        </UsersProvider>
                    </BikesProvider>
                </CategoriesProvider>
            </CalendarAndSearchProvider>
        </BrowserRouter>
    </React.StrictMode>
);
