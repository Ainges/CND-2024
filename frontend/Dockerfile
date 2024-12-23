# Verwende Node.js als Basis-Image
FROM node:16

# Setze den Arbeitsverzeichnis
WORKDIR /app

# Kopiere package.json und package-lock.json
COPY package.json package-lock.json ./

# Installiere Abhängigkeiten
RUN npm install

# Kopiere den Rest des Codes
COPY . .

# Wechsle zum Standardbenutzer "node"
USER root

# Exponiere Port 3000 für Vite
EXPOSE 3000

# Starte Vite im Dev-Modus
CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0"]

