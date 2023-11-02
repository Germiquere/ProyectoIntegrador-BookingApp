import React from 'react';

export const SkeletonGridProducts = () => {
  return (
    <div
      className={` mt-8 mb-8 rounded-xl bg-gray-200 
        `}
    >
      <div className='bg-gray-200 w-full h-48 rounded-xl'></div>
      <p className='pl-4 pt-4 bg-gray-100 '></p>
      <p className='p-4 bg-gray-100 rounded-b-xl'></p>
    </div>
  );
};
