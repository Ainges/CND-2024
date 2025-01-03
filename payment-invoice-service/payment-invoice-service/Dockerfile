# FROM mcr.microsoft.com/dotnet/aspnet:9.0 AS base
# USER $APP_UID
# WORKDIR /app
# EXPOSE 8080
# EXPOSE 8081

# FROM mcr.microsoft.com/dotnet/sdk:9.0 AS build
# ARG BUILD_CONFIGURATION=Release
# WORKDIR /src
# COPY ["payment-invoice-service.csproj", "payment-invoice-service/"]
# RUN dotnet restore "payment-invoice-service/payment-invoice-service.csproj"
# COPY . .
# WORKDIR "/src/payment-invoice-service/"
# RUN dotnet build "payment-invoice-service.csproj" -c $BUILD_CONFIGURATION -o /app/build

# FROM build AS publish
# ARG BUILD_CONFIGURATION=Release
# RUN dotnet publish "payment-invoice-service.csproj" -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false

# FROM base AS final
# WORKDIR /app
# COPY --from=publish /app/publish .
# ENTRYPOINT ["dotnet", "payment-invoice-service.dll"]

# Verwende das offizielle .NET 9 SDK-Image zum Erstellen der Anwendung
FROM mcr.microsoft.com/dotnet/sdk:9.0 AS build
WORKDIR /app

# Kopiere die Projektdatei und restore die Abhängigkeiten
COPY *.csproj ./
RUN dotnet restore

# Kopiere den Rest der Anwendung und baue sie
COPY . ./
RUN dotnet publish -c Release -o out

# Verwende das offizielle .NET 9 Runtime-Image zum Ausführen der Anwendung
FROM mcr.microsoft.com/dotnet/aspnet:9.0
WORKDIR /app
COPY --from=build /app/out .

# Exponiere den Port, auf dem die Anwendung läuft
EXPOSE 80

# Definiere den Einstiegspunkt für die Anwendung
ENTRYPOINT ["dotnet", "payment-invoice-service.dll"]
