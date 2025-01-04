using System.Net;
using System.Text.Json.Serialization;
using Microsoft.EntityFrameworkCore;
using payment_invoice_service.Data;
using payment_invoice_service.Data.Repositories;
using payment_invoice_service.Messaging;
using payment_invoice_service.Services;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddOpenApi();

// Register the ApplicationDbContext
// Get ConnectionString from environment variables in production
var connectionString = Environment.GetEnvironmentVariable("DefaultConnection") 
                       ?? builder.Configuration.GetConnectionString("DefaultConnection");

Console.WriteLine($"Connection String: {connectionString}");

builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseNpgsql(connectionString));


// Register the PaymentService and PaymentRepository
builder.Services.AddScoped<IPaymentRepository, PaymentRepository>();
builder.Services.AddScoped<PaymentService>();

// Register the InvoiceService and InvoiceRepository
builder.Services.AddScoped<IInvoiceRepository, InvoiceRepository>();
builder.Services.AddScoped<InvoiceService>();

// Register the RabbitMqListener as Scoped
builder.Services.AddScoped<RabbitMqListener>();
builder.Services.AddHostedService<RabbitMqListenerHostedService>();

// Add Swagger services
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// log swagger ui url
Console.WriteLine($"Swagger UI: http://localhost:5172/swagger/index.html");

// Add Controllers
builder.Services.AddControllers()
    .AddJsonOptions(options =>
    {
        // To handle enum serialization as strings
        options.JsonSerializerOptions.Converters.Add(new JsonStringEnumConverter());
    });

builder.Configuration
    .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
    .AddJsonFile($"appsettings.{builder.Environment.EnvironmentName}.json", optional: true, reloadOnChange: true)
    .AddEnvironmentVariables();

var app = builder.Build();

foreach (var c in builder.Configuration.AsEnumerable())
{
    Console.WriteLine(c.Key + " = " + c.Value);
}

// Drop and recreate the database
using (var scope = app.Services.CreateScope())
{
    var dbContext = scope.ServiceProvider.GetRequiredService<ApplicationDbContext>();

    // For development purposes, drop and recreate the database
    await dbContext.Database.EnsureDeletedAsync();
    await dbContext.Database.EnsureCreatedAsync();
}

app.MapControllers();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
    // Enable Swagger UI
    app.UseSwagger();
    app.UseSwaggerUI(c =>
    {
        c.SwaggerEndpoint("/swagger/v1/swagger.json", "My API V1");
    });
}

app.UseHttpsRedirection();
await app.RunAsync();