import http from "../http-common";

const getAll5 = (id, params) => {
  return http.get(`/Gerency/ProdutosDoFornecedor/${id}`, { params });
};

const getLivroTitulo = (titulo) => {
  return http.get(`/BibliotecaGerency/livroPorTitulo?titulo=${titulo}`);
};

const GerencyServer5 = {
  getAll5,
  getLivroTitulo
};


export default GerencyServer5;
