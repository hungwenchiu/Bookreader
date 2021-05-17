import React, {useEffect, useState} from 'react';


function Book() {
    const [response, setResponse] = useState(null)

    const getAllBooks = () => {
        fetch("/api/books", {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                }
            }
        )
            .then((res) => {
                return res.json();
            })
            .then((result) => {
                setResponse(result);
            })
    }

    useEffect(() => {
        getAllBooks()
    }, [])

    return (
        <div className="Book">
            All Books:
            {
                response && response.length > 0 && response.map((item) => <p>name: {item.title}; author: {item.author};
                    isbn: {item.googleBookID}</p>)
            }
        </div>
    );
}

export default Book;
