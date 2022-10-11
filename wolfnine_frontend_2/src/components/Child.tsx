import React, { FC } from "react";

interface ChildProps {
    total: number
}

const Child: FC<ChildProps> = (props: ChildProps) => {

    return (
        <>
          <h3>Tong: {props.total}</h3>
        </>
    )
}

export default Child;