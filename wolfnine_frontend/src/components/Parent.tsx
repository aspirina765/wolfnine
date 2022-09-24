import React, {useState} from "react";
import Child from "./Child";

function Parent() {
    const [number1, setNumber1] = useState(0);
    const [number2, setNumber2] = useState(0);
    const [total, setTotal] = useState(0);

    const handleChangeNumber1 = (ev: { target: { value: string } }) => {
        setNumber1(parseInt(ev.target.value));
    }

    const handleChangeNumber2 = (ev: { target: { value: string } }) => {
        setNumber2(parseInt(ev.target.value));
    }

    const handleTotal = () => {
        setTotal(number1 + number2);
    }
    return (
        <>
        <h1>Parent</h1>
        <h3>Number 1: {number1}</h3>
            <h3>Number 2: {number2}</h3>
            <input type="number" onChange={handleChangeNumber1} />
            <input type="number" onChange={handleChangeNumber2} />
            <button onClick={handleTotal}>Submit</button>
            <br/>
            -------------
        <Child total={total}/>
        </>
    )
} 
export default Parent;