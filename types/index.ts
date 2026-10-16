export type Farmer = {
  id: string;
  name: string;
  phoneNumber: string;
  email: string;
  address: string;
};

export type Product = {
  id: string;
  name: string;
  description: string;
  price: number;
  predictedPrice: number;
  quantity: number;
  unit: string;
  category: string;
  imageUrl?: string;
  harvestDate?: string;
  farmer: Farmer;
  location?: LocationData;
};

export type LocationData = {
  id: string;
  address: string;
  latitude: number;
  longitude: number;
  productId: string;
  productName: string;
  price: number;
};
