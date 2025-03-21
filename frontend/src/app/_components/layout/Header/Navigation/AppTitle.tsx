import Image from 'next/image';

export default function AppTitle() {
  return (
    <div className="flex gap-2">
      <Image alt="app-icon" src={'/images/koi.svg'} width={50} height={50} />
      <div className="text-xl my-auto hover:cursor-pointer">Koi</div>
    </div>
  );
}
