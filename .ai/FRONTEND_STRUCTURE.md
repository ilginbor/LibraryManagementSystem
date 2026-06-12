# Frontend Architecture & Structure

## Technology Stack
- Framework: React + Vite
- Language: TypeScript (or JavaScript)
- Styling: Tailwind CSS
- UI Components: Shadcn/UI (Radix UI primitives)
- State Management: Zustand (for Auth and Global State)
- Data Fetching: Axios (Custom hooks, no TanStack Query)
- Routing: React Router DOM

## Core Rules for AI
- Use functional components and hooks.
- Handle API requests using Axios in a centralized `api` service.
- Manage loading and error states manually.
- Use Shadcn/UI for consistent design (Buttons, Inputs, Tables, Dialogs).
- Store JWT token securely (localStorage or HTTP-only cookies).

## Directory Structure
src/
├── assets/
├── components/
│   ├── ui/               # Shadcn generated components
│   └── shared/           # Reusable components (Navbar, Sidebar)
├── hooks/                # Custom hooks (e.g., useAuth, useFetch)
├── pages/
│   ├── auth/             # Login, Register
│   ├── dashboard/        # Main overview
│   ├── books/            # BookList, BookDetails
│   ├── borrow/           # BorrowHistory, ActiveBorrows
│   ├── reservations/     # ReservationList
│   └── reports/          # Admin Reports
├── services/             # Axios instances and API calls
├── store/                # Zustand stores (useAuthStore.js)
├── utils/                # Helper functions (date formatting, etc.)
└── App.jsx