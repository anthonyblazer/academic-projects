import { React } from "react";

export default function Header({ user }) {
    return (
        <header className="bg-blue-900">
            <div className="m-auto w-auto max-w-screen-xl flex flex-col p-10 py-10 ">
                <h1 className="text-3xl text-white font-semibold">
                    UNCA Schedule Planner
                </h1>
                {user && (
                    <p className="text-white">Welcome, {user.first_name}</p>
                )}
            </div>
        </header>
    );
}
