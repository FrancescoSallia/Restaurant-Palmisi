<!--[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/LJ-RdF5R)-->

<h1>Restaurant Palmisi</h1>

<h3>Essen gehen war noch nie so einfach.</h3>

Meine App ist die perfekte Lösung für Restaurants, die ihren Gästen ein noch besseres Erlebnis bieten möchten. Gäste können unkompliziert reservieren und ihre Favoriten speichern. Das Servicepersonal profitiert von einer übersichtlichen Übersicht und kann sich ganz auf den Service konzentrieren. Im Gegensatz zu anderen Apps verzichten wir auf störende Werbung und kostenpflichtige Abos.
<br>
<br>
<br>

<!--
## Geplantes Design
Füge hier einige repräsentative Designs deiner App ein (z.B. aus Figma)
<p>
  <img src="./img/android_template_app_01.png" width="200">
  <img src="./img/android_template_app_02.png" width="200">
  <img src="./img/android_template_app_03.png" width="200">
  <img src="./img/android_template_app_03.png" width="200">
</p>
-->
![logoscreenfürcanva](https://github.com/user-attachments/assets/57994f0b-e6b6-4c35-8e1b-643f704480a6)
![Homescreenfürcanva](https://github.com/user-attachments/assets/3b3cb06a-11ac-4d1a-aba4-6b1f2f464791)
![speisekartescreenfürcanva](https://github.com/user-attachments/assets/a8a1928b-81e4-4404-adda-bf22825da181)
![reservierscreenfürcanva](https://github.com/user-attachments/assets/a908e450-ca54-42ca-9a2c-84cde5b6c288)
![Profilscreen](https://github.com/user-attachments/assets/0e05203d-95b9-46a0-bbe2-ddd3ee716840)







Features:
- [X] Tisch Reservierung
- [X] Gerichte Favorisieren
- [ ] Lieferservice/Abholung
- [ ] Chatbot
- [ ] Timer
- [ ] ...

Architektur(MVVM):

- MainViewModel()

Model:
- Reservierung
- User
- Meal
- Category

Datenspeicherung:
- Firestore

API Calls:
- TheMealDB Api

Dependencies/Repositories:
- Retrofit 2.9.0 - Für Netzwerkoperationen
- Firebase Storage 20.3.0 - Für remote Datenbankverwaltung
- Lottie 6.1.0 - Für GIF'S
- Logging Interceptor 4.11.0 - Für den Ablauf von Anwendungen zu protokollieren

<!--Ausblick:-->
Issue 1 (Bug):

Derzeit treten sporadische Probleme beim Hochladen und Entfernen von Bildern für Speisekarten auf. Dies erschwert die Aktualisierung der Speisekarten und führt zu einer suboptimalen Benutzererfahrung.

Issue 2 (Feature):

Selbstständige Speisekartenverwaltung.
Um die Flexibilität und Autonomie der Restaurantbesitzer zu erhöhen, schlagen ich vor, eine Funktion zur selbstständigen Verwaltung von Speisekarten zu implementieren. Dies beinhaltet einen intuitiven Editor für die Erstellung und Bearbeitung von Speisekarten sowie die zuverlässige Verwaltung von Bildern für jedes Gericht.
 
