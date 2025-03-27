import AppIcon from '../../../../../public/images/components/AppIcon';

export default function AppTitle() {
  return (
    <div className="flex gap-2">
      <div className="w-14">
        <AppIcon />
      </div>
      <div className="text-xl my-auto hover:cursor-pointer">Koi</div>
    </div>
  );
}
