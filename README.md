*Ein E-Commerce-Backend*
1. *Produktkatalog-Service*
   Verwaltung und Bereitstellung von Produktinformationen. Berechtigte Benutzer können Produkte erstellen, aktualisieren und löschen. Produktinformationen werden in einer eigenen Datenbank gespeichert und über Schnittstellen für andere
   Services verfügbar gemacht.


2. *Benutzerverwaltungsservice*
   Zuständig für die Verwaltung von Benutzerkonten, einschließlich Registrierung, Profilverwaltung und Berechtigungen.
   Authentifizierung und Autorisierung ist unter der Verwendung von JWT angedacht.


3. *Warenkorb- und Bestellservice*
   Ermöglicht die Verwaltung von Warenkörben und Bestellungen. Benutzer können Produkte in den Warenkorb legen, Bestellungen aufgeben und ihre Bestellhistorie einsehen. Der Service kommuniziert über definierte Schnittstellen mit dem Produktkatalog- und dem Benutzerservice und speichert Bestelldaten in einer eigenen Datenbank.


5. *Zahlungs- und Rechnungsservice*
   Übernimmt die Zahlungsabwicklung über externe Anbieter und erstellt Rechnungen für abgeschlossene Bestellungen.
   Speichert alle relevanten Zahlungs- und Rechnungsdaten in einer eigenen Datenbank und verknüpft diese mit den
   Bestelldaten des Warenkorb- und Bestellservices.