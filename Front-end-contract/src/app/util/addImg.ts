export function addImgPerson(v: any[]) {
  for (let i = 0; i < v.length; i++) {
    let dni = v[i].dni;
    v[i] = {
      ...v[i],
      // imgPerson: `http://172.16.10.171:8080/api/contract/showPerson?dni=${dni}`,
      imgPerson: `http://localhost:8080/api/contract/showPerson?dni=${dni}`,
    };
  }
  return v;
}
