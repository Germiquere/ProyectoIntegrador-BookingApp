import React from "react";

export const SkeletonGridProducts = () => {
  return (
    <div
      className={` mt-8 mb-8 rounded-xl shadow-md 
        `}
    >
      <div className='bg-gray-200'>
        <img className='rounded-xl  w-full h-48 object-contain' />
      </div>
      <p className='pl-4 pt-4 bg-gray-100'></p>
      <p className='p-4 bg-gray-100 '></p>
    </div>
  );
};
