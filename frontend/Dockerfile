# Verwende ein Node.js-Image für die Entwicklungsumgebung
FROM node:16-alpine

# Setze das Arbeitsverzeichnis im Container
WORKDIR /app

# Kopiere package.json und package-lock.json
COPY package.json package-lock.json ./

# Installiere die Abhängigkeiten
RUN npm install

# Kopiere den Rest des Frontend-Codes in den Container
COPY . .

# Exponiere den Vite-Entwicklungsport (standardmäßig 3000)
EXPOSE 3000

# Starte den Vite-Entwicklungsserver
CMD ["npm", "run", "dev"]
