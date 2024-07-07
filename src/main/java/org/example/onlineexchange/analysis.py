import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import mysql.connector as connection
from sklearn.linear_model import LinearRegression

try:

    db = connection.connect(host="localhost", database = 'crypto',user="root", passwd="Your-Password",use_pure=True)
    query = "Select * from data;"
    df = pd.read_sql(query, db)
    db.close()

except Exception as e:

    db.close()
    print(str(e))
    
df = df.drop(columns=["DATE", "TIMA"])

cdf = df.astype(float)

usd = np.array(cdf["USD"]).reshape(-1, 1)
eur = np.array(cdf["EUR"]).reshape(-1, 1)
toman = np.array(cdf["TOMAN"]).reshape(-1, 1)
yen = np.array(cdf["YEN"]).reshape(-1, 1)
gbp = np.array(cdf["GBP"]).reshape(-1, 1)

model0 = LinearRegression()

x_usd = usd * usd

model0.fit(x_usd, usd)

a = model0.coef_[0][0]
b = model0.intercept_[0]

plt.scatter(x_usd, usd)
plt.plot(x_usd, a * x_usd + b, color="red")
plt.xlabel("USD")
plt.ylabel("USD")
plt.show()

# first model for EUR
model1 = LinearRegression()

x_eur = eur * usd

model1.fit(x_eur, usd)

a = model1.coef_[0][0]
b = model1.intercept_[0]

plt.scatter(x_eur, usd)
plt.plot(x_eur, a * x_eur + b, color="red")
plt.xlabel("EUR")
plt.ylabel("USD")
plt.show()

# second model for Toman
model2 = LinearRegression()

x_toman = toman * usd

model2.fit(x_toman, usd)

a = model2.coef_[0][0]
b = model2.intercept_[0]

plt.scatter(x_toman, usd)
plt.plot(x_toman, a * x_toman + b, color="red")
plt.xlabel("Toman")
plt.ylabel("USD")
plt.show()

# third model for YEN
model3 = LinearRegression()

x_yen = yen * usd

model3.fit(x_yen, usd)

a = model3.coef_[0][0]
b = model3.intercept_[0]

plt.scatter(x_yen, usd)
plt.plot(x_yen, a * x_yen + b, color="red")
plt.xlabel("YEN")
plt.ylabel("USD")
plt.show()

# fourth model for GBP
model4 = LinearRegression()

x_gbp = gbp * usd

model3.fit(x_gbp, usd)

a = model3.coef_[0][0]
b = model3.intercept_[0]

plt.scatter(x_gbp, usd)
plt.plot(x_gbp, a * x_gbp + b, color="red")
plt.xlabel("GBP")
plt.ylabel("USD")
plt.show()
