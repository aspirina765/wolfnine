import { EnumType } from 'typescript';

export const generateEnumArray = (enumType: any): Array<any> => {
  return Object.keys(enumType).filter((v) => !isNaN(Number(v))) as (keyof typeof enumType)[];
};
