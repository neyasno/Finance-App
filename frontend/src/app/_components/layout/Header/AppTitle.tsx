import { Link } from '@/i18n/routing';
import AppIcon from '../../../../../public/images/components/AppIcon';
import { ERoutes } from '@/enums';

export default function AppTitle() {
  return (
    <Link href={ERoutes.DEFAULT}>
      <div className="flex gap-2">
        <div className="w-14 hidden sm:flex">
          <AppIcon />
        </div>
        <div className="text-xl my-auto hover:cursor-pointer">Koi</div>
      </div>
    </Link>
  );
}
