import axios from 'axios';
import React from 'react';
import { useEffect } from 'react';
import { useState } from 'react';

const List = () => {

    let token = localStorage.getItem('accessToken');
                // || '';

    axios.post('http://localhost:8888/likes/', {
    headers: {
        'Authorization': token,
    }
    }).then(response => response.json())
      .then(response => {
        console.log(response.data);
    })
// =================================================




    const [list, setList] = useState([]);

    useEffect= () => {
        // axios
    }

    return (
        <div>
            
        </div>
    );
};

export default List;