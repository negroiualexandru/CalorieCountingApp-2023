# CalorieCountingApp 🍎
### *Macro-Nutrient Tracker & Diet Balancer*

[![Android](https://img.shields.io/badge/Android-3DDC84?style=flat&logo=android&logoColor=white)](https://developer.android.com/) [![Java](https://img.shields.io/badge/Java-007396?style=flat&logo=java&logoColor=white)](https://www.java.com/) [![SQLite](https://img.shields.io/badge/SQLite-003B57?style=flat&logo=sqlite&logoColor=white)](https://www.sqlite.org/) [![XML](https://img.shields.io/badge/XML-FFA500?style=flat&logo=xml&logoColor=white)](https://www.w3.org/XML/)

**CalorieCountingApp** is a native Android application built with **Java** and **SQLite** designed to help users achieve their fitness goals through data-driven nutrition. Whether the goal is muscle gain, fat loss, or weight maintenance, the app calculates the required daily intake and tracks progress in real-time.

To help users maintain a balanced diet, the app features a **Macro-Balancing Engine**. It continuously monitors your Protein, Carb, and Fat intake throughout the day and dynamically updates the "Recommended Foods" list based on whichever macronutrient has the largest remaining deficit.

---

## 🚀 Key Features

* **Adaptive Recommendations:** The app identifies which macronutrient you are lacking most at any given moment and suggests specific food sources to fill that gap.
    * *Logic:* If you have met your protein goal but are low on carbohydrates, the recommendation engine switches to suggest carb-rich foods (e.g., rice, pasta) for your next meal.
* **Goal-Based Targets:** Automatically calculates calorie and macro limits based on your specific objective: **Muscle Gain**, **Fat Loss**, or **Maintenance**.
* **Daily Log:** A persistent history of all meals consumed, allowing users to identify eating patterns over time.
* **Progress Dashboard:** Clear visualization of remaining calories and macro-breakdowns to keep users on track throughout the day.

---

## 🛠️ Tech Stack

| Component | Technology | Role |
| :--- | :--- | :--- |
| **Language** | Java | Implemented the conditional logic for the macro-deficit calculator. |
| **UI/UX** | XML | Designed the layout for dashboards, food entry forms, and lists. |
| **Database** | SQLite | Stores user profiles, food history, and nutritional data locally on the device. |

---

## 📱 Interface Preview

<div align="center" style="display: flex; justify-content: center; flex-wrap: wrap; gap: 10px;">
  <img src="https://github.com/user-attachments/assets/e170fdd5-45b2-4234-bc3f-18743b7ab6ed" alt="Home Dashboard" width="16%" />
  <img src="https://github.com/user-attachments/assets/c4d37f51-f887-4918-8b42-50929ae8a93f" alt="Food Entry" width="16%" />
  <img src="https://github.com/user-attachments/assets/4679625c-d3be-45f7-8066-5787b63e441f" alt="Macro Breakdown" width="16%" />
  <img src="https://github.com/user-attachments/assets/30bf9ee1-be3e-4ad6-90b8-395a834988fb" alt="Recommendation Engine" width="16%" />
  <img src="https://github.com/user-attachments/assets/e7964522-4339-4887-a44b-fe1d30ec08b8" alt="Profile Settings" width="16%" />
  <img src="https://github.com/user-attachments/assets/1c2656f4-9a63-44c8-ad53-1d9ab7f5a0ec" alt="Calorie Calculator" width="16%" />
</div>
