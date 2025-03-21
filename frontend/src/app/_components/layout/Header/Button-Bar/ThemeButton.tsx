"use client"
import { useTheme } from 'next-themes'
import React from 'react'

export default function ThemeButton() {

    const {theme , setTheme} = useTheme();

  return (
    <button className='text-center justify-center items-center p-3 rounded-full dark:hover:bg-white hover:bg-black transition-all ' onClick={()=>{
        console.log(theme)
        setTheme(theme === "dark" ? "light" : "dark");
    }}>
        {theme === "dark" ?  <>☀</>: <>🌑</>}
    </button>
  )
}
