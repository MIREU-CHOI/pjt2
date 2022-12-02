import React from 'react';
import DaumPostcode from 'react-daum-postcode';
import "../../css/post.css";
import { isLabelWithInternallyDisabledControl } from '@testing-library/user-event/dist/utils';

// const postCodeStyle = {
   
//     display: "block",
//     position: "absolute",
//     top: "20%",
//     width: "400px",
//     height: "400px",
//     padding: "7px",
//     zIndex: 100, 
//   };


  const postCodeStyle2 = {
   
    display: "block",
    position: "absolute",
    width: "400px",
    height: "100px",
    padding: "7px",
    zIndex: 100, 
    background :  "white"
  };

  const postCodeStyle3 = {
   
    display: "block",
    position: "absolute",
    top: "20%",
    width: "400px",
    height: "500px",
    padding: "7px",
    zIndex: 100, 
    
  };

const Post = ({openpost,handleChage2,handleChage3}) => {
    // https://infodon.tistory.com/117
    const complete = (data) =>{
        let fullAddress = data.address;
        let extraAddress = '';

        if (data.addressType === 'R') {
            if (data.bname !== '') {
                extraAddress += data.bname;
            }
            if (data.buildingName !== '') {
                extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName);
            }
            fullAddress += (extraAddress !== '' ? ` (${extraAddress})` : '');
        }
        console.log(data)
        console.log(fullAddress)
        console.log(data.zonecode)

        // props.setcompany({
        //     ...props.company,
        //     address:fullAddress,
        // })
        handleChage2(data.zonecode);
        openpost();
    }

    return (
        <div>
        <div style={postCodeStyle3}>
        {/* className='pop2' */}
            <div style={postCodeStyle2}><button onClick={openpost}>닫기</button></div>
            <DaumPostcode
                className='postmodal'
                autoClose
                onComplete={complete}
                // style={postCodeStyle}
            />
            {/* <button onClick={handle.clickButton}>toggle</button> */}
        </div>
        </div>
        
    );
};

export default Post;