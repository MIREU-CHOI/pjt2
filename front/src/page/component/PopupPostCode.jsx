import React, { useState } from 'react';
import DaumPostcode from "react-daum-postcode";
 
const PopupPostCode = ({setfullAddress, fullAddress, zonecode,setZonecode, onClose}) => {

	// 우편번호 검색 후 주소 클릭 시 실행될 함수, data callback 용
    const handlePostCode = (data) => {
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
        setZonecode(data.zonecode)
        setfullAddress(fullAddress)
        onClose()
    }
 
    const postCodeStyle = {
        display: "block",
        position: "absolute",
        top: "10%",
        width: "600px",
        height: "600px",
        padding: "7px",
      };
 
    return(
        <div>
            {zonecode}
            {fullAddress}
            <DaumPostcode style={postCodeStyle} onComplete={handlePostCode} />
            {/* 닫기 버튼 생성 */}
            <button type='button' onClick={() => {onClose()}} className='btn btn-primary'>
                닫기</button>
        </div>
    )
}
 
export default PopupPostCode;