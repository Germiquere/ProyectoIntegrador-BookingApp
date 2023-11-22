import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.jsx';
import './index.css';
import { BrowserRouter } from 'react-router-dom';
import { CalendarAndSearchProvider } from './context/CalendarSearchContext.jsx';
import { CategoriesProvider } from './context/CategoriesContext.jsx';
import { BikesProvider } from './context/BikesContext.jsx';
import { UsersProvider } from './context/UsersContext.jsx';
import { CharacteristicsProvider } from './context/CharacteristicsContext.jsx';
import { FavoritesProvider } from './context/FavoritesContext.jsx';
import { PoliciesProvider } from './context/PoliciesContext.jsx';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter>
      <CalendarAndSearchProvider>
        <CategoriesProvider>
          <BikesProvider>
            <UsersProvider>
              <CharacteristicsProvider>
                <FavoritesProvider>
                  <PoliciesProvider>
                    <App />
                  </PoliciesProvider>
                </FavoritesProvider>
              </CharacteristicsProvider>
            </UsersProvider>
          </BikesProvider>
        </CategoriesProvider>
      </CalendarAndSearchProvider>
    </BrowserRouter>
  </React.StrictMode>
);
