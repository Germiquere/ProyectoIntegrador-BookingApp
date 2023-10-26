import React, { useEffect, useState } from "react";

export const LineaBreak = ({ text }) => {
    const [formattedText, setFormattedText] = useState(null);

    useEffect(() => {
        if (text === undefined || text === null) {
            setFormattedText("");
        } else {
            const lines = text.split("\n");
            setFormattedText(
                lines.map((line, index) => (
                    <React.Fragment key={index}>
                        {line && <p>{line}</p>}
                        {index < lines.length - 1 && <br />}
                    </React.Fragment>
                ))
            );
        }
    }, [text]);

    return <div>{formattedText}</div>;
};
