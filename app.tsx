import { ChangeEvent, FormEvent, useState } from "react";
import style  from "./login.module.css";

export default function Login() {
    const[users, setUsers] = useState({
        name: "",
        email: "",
        password: ""
    })

    const onchangeData = (e: ChangeEvent<HTMLInputElement>) =>{
        const {name, value} = e.target;
        setUsers((prev) => ({
            ...prev,
            [name]: value
        }))
    }

    const onSubmitData = async (e: FormEvent<HTMLFormElement>) =>{
        e.preventDefault()
        try {
            const res = await fetch('http://localhost:8086/auth/register', {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(users),
            });
            if (!res.ok) {
                throw new Error("Failed to fetch users");
            }
        } catch (error) {
            console.error(error);
        }
    }



    return (
        <div className={style.Login}>
            <form className={style.loginForm} onSubmit={onSubmitData}>
                <div className={style.formDiv}>
                    <label htmlFor="Name">
                        Name
                    </label>
                    <input type="text" name="name" id="Name" placeholder="name"
                           onChange={onchangeData}
                           value={users.name}
                    />
                </div>
                <div className={style.formDiv}>
                    <label htmlFor="Email">
                        E-mail
                    </label>
                    <input type="email" name="email" id="Email" placeholder="email"
                           onChange={onchangeData}
                           value={users.email}
                    />
                </div>
                <div className={style.formDiv}>
                    <label htmlFor="Password">
                        Password
                    </label>
                    <input type="password" name="password" id="Password" placeholder="password"
                           onChange={onchangeData}
                           value={users.password}
                    />
                </div>
                <button className={style.btnLogin}>Login</button>
            </form>
        </div>
    )
}