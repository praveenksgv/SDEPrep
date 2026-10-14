**Zluri Interview Round 2**

Coupon Management System
Design a coupon management system for a large online store to attract new customers and encourage repeat purchases. The system should support the complete coupon lifecycle, from creation and validation to redemption, tracking, and expiry.
Functional Requirements
1. Coupon Generation
   - Generate unique coupon codes for different promotions, discounts, and offers.
   - Support configurable parameters, including percentage discounts, fixed-amount discounts, validity periods, and usage limits.
2. Coupon Validation
   - Validate coupon codes entered during checkout to ensure they are legitimate and applicable to the current order.
   - Check eligibility based on validity periods, usage limits, eligible products, and eligible customers.
3. Coupon Redemption
   - Apply the discount or promotional offer associated with a valid coupon during checkout.
   - Update the order details and total amount payable to reflect the applied discount.
4. Coupon Tracking
   - Track coupon usage to analyze customer behavior and measure the effectiveness of promotional campaigns.
   - Maintain a usage log containing the coupon code, order ID, customer ID, discount applied, and redemption timestamp.
5. Coupon Expiry and Usage Limits
   - Enforce coupon validity periods and prevent expired coupons from being used.
   - Enforce configured usage limits to prevent coupons from being redeemed beyond their allowed limits.
6. API Access
   - Provide well-defined APIs for external applications to create, manage, and retrieve coupon information.





**Harness First Round**
Design a Visitor Management System (VMS) for a gated society/apartment community. The system is used primarily by security guards at the gate and residents inside the society to manage and control visitor entry.


1. A security guard should be able to record the arrival of a person at the gate for a specific flat/resident, along with the purpose.

2. The resident should be able to respond to the request by choosing one of:

   * allow entry

   * deny entry

   * choices can be extended in future

3. The system should present the resident only the choices that are valid for that arrival type.

4. the guard should be able to mark the person as entered.

5. the guard should be able to mark exit when the person leaves.

6. The system must prevent invalid operations (e.g., cannot mark entry if resident denied; cannot mark exit before entry).