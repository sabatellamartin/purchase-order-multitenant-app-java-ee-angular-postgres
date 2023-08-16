export enum Estado{
  CREADO, // Tu pedido esta siendo validado
  VALIDADO, // Tu pedido esta siendo procesado
  PROCESADO, // Tu pedido esta Listo para enviar
  ENVIADO, // Tu pedido va en camino
  ENTREGADO, // Tu pedido se entrego con exito
  CANCELADO // Tu pedido fue cancelado
}
