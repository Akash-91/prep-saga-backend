✅ Step 1: PrepSaga GitHub Setup (Repo + Git CLI)
Let’s confirm what’s done vs pending.

🧾 Your Previous Setup Summary (from past chats):
You have:

A backend folder: prep-saga-backend (Java + Spring Boot)

A frontend folder: prep-saga-ui (React/Angular)

A GitHub repo named: prep-saga under username Akash-91

Some initial commits already pushed

🎯 Today’s Step-by-Step Objective:
Goal: Confirm full GitHub setup for both frontend and backend

✅ 1. Check GitHub Repo
Open terminal and confirm you're inside the project root folder. Then:

bash
Copy
Edit
cd prep-saga-backend
git remote -v
Expected output:

bash
Copy
Edit
origin  https://github.com/Akash-91/prep-saga.git (fetch)
origin  https://github.com/Akash-91/prep-saga.git (push)
If not set or you want to re-add:

bash
Copy
Edit
git remote add origin https://github.com/Akash-91/prep-saga.git
✅ 2. Commit and Push Current Backend Code
bash
Copy
Edit
git add .
git commit -m "Initial full backend code structure added - PrepSaga"
git push origin main
🔁 If your default branch is master instead of main, replace accordingly.

✅ 3. Push Frontend Code
Repeat for UI code:

bash
Copy
Edit
cd ../prep-saga-ui
git init
git remote add origin https://github.com/Akash-91/prep-saga.git
git checkout -b ui
git add .
git commit -m "Initial UI code pushed to ui branch - PrepSaga"
git push origin ui
🎯 This way you’ll keep main for backend and ui for frontend — simple and clean.

✅ 4. Install GitHub CLI (if not done)
To use gh (GitHub CLI) commands:

bash
Copy
Edit
brew install gh            # for mac
sudo apt install gh        # for Ubuntu/Debian
choco install gh           # for Windows
Login:

bash
Copy
Edit
gh auth login
Follow interactive login – choose GitHub.com, then browser login.

🎯 Once This Is Done:
Reply ✅ when:

Both backend and frontend are pushed

gh CLI is installed and authenticated

Then Step 2: we’ll move to Dockerizing both services, validating locally using docker-compose, and preparing for AKS push.

Let me know when you’re ready to continue.



How to Set GitHub CLI Context to Your Repos
Assuming you've already logged in with:

bash
Copy
Edit
gh auth login
Now we’ll associate your CLI with your existing GitHub repositories:

🔁 Step 1: Clone (or Reconnect) Your Repositories (if not already)
If you already cloned locally, go into them:

bash
Copy
Edit
cd ~/your/path/prep-saga-backend
If not, clone them via gh:

bash
Copy
Edit
gh repo clone Akash-91/prep-saga
📎 Step 2: Set gh to work inside the repo context
Go into your backend repo directory:

bash
Copy
Edit
cd prep-saga-backend
Now run this to confirm it's connected:

bash
Copy
Edit
gh repo view --web
This should open your GitHub repo in the browser if configured correctly.

Repeat this for your frontend (prep-saga-ui) if needed.

🛠️ Step 3: Use gh Commands in Repo Context
Now you can:

Create CI/CD workflows:

bash
Copy
Edit
gh workflow list
Manage secrets (we’ll do this soon):

bash
Copy
Edit
gh secret set AZURE_CLIENT_ID
Create/push containers:

bash
Copy
Edit
gh auth status
gh run list


Stage 1 (Build):

Uses Maven with JDK 17 to build your app

Efficient caching of Maven dependencies

Skips tests for faster Docker build

Stage 2 (Run):

Uses a lightweight JDK 17 Alpine image

Only includes the built .jar file (no Maven, no sources)

Very small final image (~200MB or less)

🛠️ Build and run it locally:
bash
Copy
Edit
docker build -t prepsaga-backend .
docker run -p 8082:8082 prepsaga-backend