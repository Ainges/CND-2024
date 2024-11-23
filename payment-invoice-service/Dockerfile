FROM mcr.microsoft.com/dotnet/aspnet:9.0 AS base
USER $APP_UID
WORKDIR /app
EXPOSE 8080
EXPOSE 8081

FROM mcr.microsoft.com/dotnet/sdk:9.0 AS build
ARG BUILD_CONFIGURATION=Release
WORKDIR /src
COPY ["payment-invoice-service/payment-invoice-service/payment-invoice-service.csproj", "payment-invoice-service/payment-invoice-service/"]
RUN dotnet restore "payment-invoice-service/payment-invoice-service/payment-invoice-service.csproj"
COPY . .
WORKDIR "/src/payment-invoice-service/payment-invoice-service"
RUN dotnet build "payment-invoice-service.csproj" -c $BUILD_CONFIGURATION -o /app/build

FROM build AS publish
ARG BUILD_CONFIGURATION=Release
RUN dotnet publish "payment-invoice-service.csproj" -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "payment-invoice-service.dll"]
