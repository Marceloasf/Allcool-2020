import { useEffect, useState, useRef } from 'react';

export const useLoading = () => {
  let mounted = useRef(false);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    mounted.current = true;

    return () => {
      mounted.current = false;
    };
  }, []);

  const fetch = (promise: Promise<any>): Promise<any> => {
    setLoading(true);

    return promise.finally(() => mounted.current && setLoading(false));
  };

  return [loading, fetch] as [boolean, (promise: Promise<any>) => Promise<any>];
};
