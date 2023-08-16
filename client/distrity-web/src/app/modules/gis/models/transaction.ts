
export class Transaction {
  operation: string; // insert, update, delete
  namespace: string; // Namespace
  layer: string; // Tenant name
  projection: string; // srsProjection
  features: any; // feature
  constructor() {}
}
