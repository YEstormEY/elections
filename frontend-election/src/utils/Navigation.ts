// src/utils/navigation.ts
import { useRouter } from 'vue-router';

export function useNavigation() {
  const router = useRouter();

  const goToLogin = () => {
    router.push('/login');
  };

  const goToHome = () => {
    router.push('/');
  };

  const goToRegister = () => {
    router.push('/register');
  };

  //footer
  const goToContact = () => {
    router.push('/contact');
  }

  const goToNieuwsbrief = () => {
    router.push('/Nieuwsbrief');
  }

  return { goToLogin, goToHome, goToRegister, goToContact, goToNieuwsbrief };
}
