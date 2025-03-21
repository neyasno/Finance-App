import { ReactNode } from "react";

type DropdownProps = {
    enabled?: boolean
    children: ReactNode
    className?: string
};

export default function DropdownMenu({children, enabled, className}: DropdownProps) {

    return (
        <>
            {enabled && (
                <div className={`absolute ${className}`}>
                    <div className="mt-2 bg-white_d dark:bg-black_l 
                                    border-2 border-black_ll dark:border-white rounded-md">
                        {children}
                    </div>
                </div> 
            )}
        </>        
    );
}