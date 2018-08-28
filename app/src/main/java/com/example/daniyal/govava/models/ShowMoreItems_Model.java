package com.example.daniyal.govava.models;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;

import java.io.Serializable;

/**
 * Created by Pc on 4/26/2018.
 */

//@SuppressLint("ParcelCreator")
public class ShowMoreItems_Model  {

    private String salePrice,name,largeImage,customerRating,customerRatingImage,categoryNode,stock;
    private String shortDescription,longDescription,offerType,isTwoDayShippingEligible,availableOnline,standardShipRate;
    private String marketplace,modelNumber,productUrl,numReviews,addToCartUrl;
    private String itemId,parentItemId;
    private String serviceName;

    private String viewItemURL;

    public ShowMoreItems_Model(String serviceName,String viewItemURL,String itemId,String largeImage,String salePrice, String name) {
        this.salePrice = salePrice;
        this.name = name;
        this.largeImage = largeImage;
        this.itemId = itemId;
        this.serviceName = serviceName;
        this.viewItemURL = viewItemURL;
    }

    public ShowMoreItems_Model(String name,String salePrice,String largeImage,String serviceName)
    {
        this.serviceName = serviceName;
        this.name = name;
        this.salePrice = salePrice;
        this.largeImage = largeImage;
    }

    public String getName() {
        return name;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public String getCategoryNode() {
        return categoryNode;
    }

    public String getOfferType() {
        return offerType;
    }

    public String getIsTwoDayShippingEligible() {
        return isTwoDayShippingEligible;
    }

    public String getAvailableOnline() {
        return availableOnline;
    }

    public String getMarketplace() {
        return marketplace;
    }

    public String getAddToCartUrl() {
        return addToCartUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLargeImage(String largeImage) {
        this.largeImage = largeImage;
    }

    public void setCustomerRating(String customerRating) {
        this.customerRating = customerRating;
    }

    public void setCustomerRatingImage(String customerRatingImage) {
        this.customerRatingImage = customerRatingImage;
    }

    public void setCategoryNode(String categoryNode) {
        this.categoryNode = categoryNode;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public void setIsTwoDayShippingEligible(String isTwoDayShippingEligible) {
        this.isTwoDayShippingEligible = isTwoDayShippingEligible;
    }

    public void setAvailableOnline(String availableOnline) {
        this.availableOnline = availableOnline;
    }

    public void setMarketplace(String marketplace) {
        this.marketplace = marketplace;
    }

    public void setNumReviews(String numReviews) {
        this.numReviews = numReviews;
    }

    public void setAddToCartUrl(String addToCartUrl) {
        this.addToCartUrl = addToCartUrl;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setParentItemId(String parentItemId) {
        this.parentItemId = parentItemId;
    }

    public String getViewItemURL() {
        return viewItemURL;
    }

    public void setViewItemURL(String viewItemURL) {
        this.viewItemURL = viewItemURL;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getlargeImage() {
        return largeImage;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public String getname() {
        return name;
    }

    public String getItemId() {
        return itemId;
    }

    public String getParentItemId() {
        return parentItemId;
    }

    public String getCustomerRating() {
        return customerRating;
    }

    public String getCustomerRatingImage() {
        return customerRatingImage;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getNumReviews() {
        return numReviews;
    }

    public String getStandardShipRate() {
        return standardShipRate;
    }

    public void setStandardShipRate(String standardShipRate) {
        this.standardShipRate = standardShipRate;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

   /* @Override
    public int getColumnSpan() {
        return 0;
    }

    @Override
    public int getRowSpan() {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }*/
}
