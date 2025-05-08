🎵 TuneHub
TuneHub is a sleek and powerful Spring Boot-based web application that lets you manage your own Spotify playlists like a pro. With a modern interface powered by Thymeleaf and full integration with the Spotify Web API, you can search, play, and organize songs with ease — all from your browser.

🚀 Features
🔍 Real-time Spotify Search – Find real songs directly from Spotify’s vast music library

▶️ Built-in Player – Play your favorite tracks through the embedded Spotify player

➕ Custom Playlists – Create unlimited playlists and add any song you like

❌ Remove Songs or Entire Playlists – Stay in control of your music collection

💾 Persistent Storage – Playlists are saved in a CSV file to keep your progress even after server restarts

🎨 Simple UI – Clean HTML/CSS frontend using Thymeleaf for dynamic rendering

🛠️ How It Works
TuneHub was bootstrapped using start.spring.io and runs on Spring Boot.
It integrates with the Spotify Web API, so you’ll need a Spotify developer account.

🔐 Setup Requirements
Before running TuneHub, you must:

Register your app on Spotify for Developers.

Get your Client ID and Client Secret.

Configure them in the application (e.g., via application.properties or environment variables).

Make sure your redirect URI matches the one configured in your Spotify dashboard.

📂 Data Storage
All playlist data is stored in a local CSV file, acting as a lightweight database. This ensures that your playlists are not lost when the server is restarted.

🖥️ Tech Stack
Backend: Java + Spring Boot

Frontend: Thymeleaf, HTML, CSS

Integration: Spotify Web API

Persistence: CSV-based file system

🌐 Getting Started
Clone the repo:

bash
Copia
Modifica
git clone https://github.com/antonio/tunehub
cd tunehub
Configure your Spotify credentials, then run:

bash
Copia
Modifica
./mvnw spring-boot:run
Open your browser and go to http://localhost:8080 to start using TuneHub!

📌 Final Notes
This project is perfect for exploring API integration, managing user state without a full database, and building user-friendly interfaces using Spring technologies. TuneHub is not just a playlist manager — it's your personalized music control center.

