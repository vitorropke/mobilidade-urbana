import pyproj

#Decimal GPS
entradaProjecao = pyproj.Proj(init="epsg:4326")
#Pseudo-mercator X,Y
saidaProjecao = pyproj.Proj(init="epsg:3857")

latitudeEntrada = "-5.1533675"
longitudeEntrada = "-37.363647"

saidaX, saidaY = pyproj.transform(entradaProjecao, saidaProjecao, longitudeEntrada, latitudeEntrada)

print(saidaX)
